import request from '@/utils/request'

export default {
  //生成订单
  createOrders(courseId) {
    return request({
      url: '/eduorder/order/createOrder/' + courseId,
      method: 'post'
    })
  },

  //根据订单id查询订单信息
  getOrdersInfo(orderId) {
    return request({
      url: '/eduorder/order/getOrderInfo/' + orderId,
      method: 'get'
    })
  },

  //生成二维码的方法
  createQRcode(orderNo) {
    return request({
      url: '/eduorder/pay-log/createQrCode/' + orderNo,
      method: 'get'
    })
  },

  //查询订单状态的方法
  queryPayStatus(orderNo) {
    return request({
      url: '/eduorder/pay-log/queryPayStatus/' + orderNo,
      method: 'get'
    })
  },

  //获取用户所有订单
  getOrderList(){
    return request({
      url: `/eduorder/order/orderList`,
      method: 'get'
    })
  },

  //根据订单id删除订单
  removeOrderById(orderId){
    return request({
      url: `/eduorder/order/removeOrder/${orderId}`,
      method: 'delete'
    })
  },

  //查询订单表中订单的状态 判断课程是否购买
  isBought(courseId) {
    return request({
      url: `/eduorder/order/isBought/${courseId}`,
      method: 'get'
    })
  },
}
