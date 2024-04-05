package com.zm.cloud.service.imp;

import com.zm.cloud.apis.AccountFeignApi;
import com.zm.cloud.apis.StorageFeignApi;
import com.zm.cloud.entities.Order;
import com.zm.cloud.mapper.OrderMapper;
import com.zm.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
public class OrderServiceImp implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StorageFeignApi storageFeignApi;//订单微服务通过openfeign调用库存微服务
    @Resource
    private AccountFeignApi accountFeignApi;//订单微服务通过openfeign调用账户微服务


    @Override
    @GlobalTransactional(name = "zm-transactional-order",rollbackFor = Exception.class)
    public void createOrder(Order order) {
        //XID全局事务的检查通过seata的RootContext获取
        String xid = RootContext.getXID();
        //创建订单
        log.info("==================>开始新建订单"+"\t"+"xid_order:" +xid+"\n");
        //创建之前有订单状态，0是创建中，1代表创建完成
        order.setStatus(0);
        //再开始创建订单，拿到返回值进行判断,大于0代表插入一条记录成功
        int i = orderMapper.insert(order);
        //插入成功之后再获得MySQL的实体对象
        Order orderFromDB = null;
        if (i>0) {
            orderFromDB=orderMapper.selectOne(order);
            log.info("-------> 新建订单成功，orderFromDB info: "+orderFromDB+"\n");
            //新订单创建成功后开始调用storageFeignApi减少一个库存
            log.info("-------> 订单微服务开始调用storageFeignApi减少一个库存"+"\n");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("-------> 订单微服务开始调用storageFeignApi减少一个库存操作完成！！！");

            //新订单创建成功后开始调用accountFeignApi扣用户的钱
            log.info("-------> 订单微服务开始调用accountFeignApi扣除账户余额"+"\n");
            accountFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getMoney());
            log.info("-------> 订单微服务开始调用accountFeignApi扣除账户余额操作完成！！！");
            System.out.println();
            //订单完成修改状态为1
            log.info("-------> 正在修改订单状态....");
            orderFromDB.setStatus(1);
            //构建查询条件
            Example example = new Example(Order.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId",orderFromDB.getUserId());
            criteria.andEqualTo("status",0);
            //使用上面创建的查询条件，orderMapper调用updateByExampleSelective来更新数据库中的订单状态。
            //这个方法会找到所有满足条件的订单，将这些订单的status字段更新为orderFromDB中的status值
            int  updateResult = orderMapper.updateByExampleSelective(orderFromDB, example);
            log.info("-------> 修改订单状态完成"+"\t"+updateResult);
            log.info("-------> orderFromDB info: "+orderFromDB);
        }
        System.out.println();
        log.info("==================>结束新建订单"+"\t"+"xid_order:" +xid);
    }
}