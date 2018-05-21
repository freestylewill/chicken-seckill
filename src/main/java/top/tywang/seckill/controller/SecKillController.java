package top.tywang.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.tywang.seckill.domain.OrderInfo;
import top.tywang.seckill.domain.SecKillOrder;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.result.CodeMsg;
import top.tywang.seckill.service.GoodsService;
import top.tywang.seckill.service.OrderService;
import top.tywang.seckill.service.SecKillService;
import top.tywang.seckill.service.SecKillUserService;
import top.tywang.seckill.vo.SeckillGoodsVo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author dongwei
 * @date 2018/05/21
 * Time: 19:13
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecKillService seckillService;

    @RequestMapping("/do_seckill")
    public String list(Model model, SecKillUser user,
                       @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }
        //判断库存
        SeckillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        SecKillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            model.addAttribute("errmsg", CodeMsg.SECKILL_REPEAT.getMsg());
            return "miaosha_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.secKill(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
