var app = require('koa')();////引入 koa模块
var router = require('koa-router')();/*引入是实例化路由*/

// router.get('/', function *(next) {
//     this.body = 'hello koa !'
// });

// router.get('/api', function *(next) {
//     this.body = 'test data'
// });

//服务端口和网页端口在webpack.config.js配置
// 首页 —— 广告（超值特惠）
var homeAdData = require('./home/ad.js')//引入js文件
router.get('/api/homead', function *(next) {//显示页面地址http://127.0.0.1:3002/api/homead
    
    console.log('首页 —— 广告（超值特惠）')//控制台上输出信息

    this.body = homeAdData
});

// 首页 —— 推荐列表（猜你喜欢）
var homeListData = require('./home/list.js')
router.get('/api/homelist/:city/:page', function *(next) {//:city代表参数
    /* function *(next)
    generator函数可以借助 yield 在需要的时候才继续执行剩余的语句，并且传递回一个值。
    回调函数！
    更关键的是，借助generator我们可以用同步的逻辑来表达异步的流程，而不需要嵌套回调。
 */
    console.log('首页 —— 推荐列表（猜你喜欢）')

    // 参数
    const params = this.params//获取当前参数 即 /:city/:page
    const paramsCity = params.city
    const paramsPage = params.page

    console.log('当前城市：' + paramsCity)
    console.log('当前页数：' + paramsPage)

    this.body = homeListData
});

// 搜索结果页 - 搜索结果 - 三个参数
var searchListData = require('./search/list.js')
router.get('/api/search/:page/:city/:category/:keyword', function *(next) {
    console.log('搜索结果页 - 搜索结果')

    // 参数
    const params = this.params
    const paramsPage = params.page
    const paramsCity = params.city
    const paramsCategory = params.category
    const paramsKeyword = params.keyword

    console.log('当前页数：' + paramsPage)
    console.log('当前城市：' + paramsCity)
    console.log('当前类别：' + paramsCategory)
    console.log('关键字：' + paramsKeyword)

    this.body = searchListData
})
// 搜索结果页 - 搜索结果 - 两个参数
router.get('/api/search/:page/:city/:category', function *(next) {
    console.log('搜索结果页 - 搜索结果')

    // 参数
    const params = this.params
    const paramsPage = params.page
    const paramsCity = params.city
    const paramsCategory = params.category

    console.log('当前页数：' + paramsPage)
    console.log('当前城市：' + paramsCity)
    console.log('当前类别：' + paramsCategory)

    this.body = searchListData
})

// 详情页 - 商户信息
const detailInfo = require('./detail/info.js')
router.get('/api/detail/info/:id', function *(next) {
    console.log('详情页 - 商户信息')

    const params = this.params
    const id = params.id

    console.log('商户id: ' + id)

    this.body = detailInfo
})
// 详情页 - 用户评论
const detailComment = require('./detail/comment.js')
router.get('/api/detail/comment/:page/:id', function *(next) {
    console.log('详情页 - 用户点评')

    const params = this.params
    const page = params.page
    const id = params.id

    console.log('商户id: ' + id)
    console.log('当前页数: ' + page)

    this.body = detailComment
})

// 订单列表
const orderList = require('./orderlist/orderList.js')
router.get('/api/orderlist/:username', function *(next) {
    console.log('订单列表')

    const params = this.params
    const username = params.username
    console.log('用户名：' + username)

    this.body = orderList
})


// 购买
router.post('/api/order', function *(next) {//提交

    console.log('buy')

    /*
     this.body = {
     errno: 1,
     msg: 'buy not ok'
     }
     */


    this.body = {
        errno: 0,
        msg: 'buy ok',// 弹出信息ok
    }


});

// 登录
router.post('/api/login', function *(next) {
    console.log('login')

    /*
    this.body = {
        errno: 1,
        msg: 'loing not ok'
    }
    */

    
    this.body = {
        errno: 0,
        msg: 'loing ok',
        token: 'aaaaaaaaaaaa'
    }
    

});

// 获取短信验证码
router.post('/api/sms', function *(next) {

    console.log('获取短信验证码')

    this.body = {
        errno: 0,
        msg: 'ok',
        code: 'md5(123456)'//验证码123456
    }
});


// 提交评论
router.post('/api/submitComment', function *(next) {
    console.log('提交评论')

    // 获取参数

    this.body = {
        errno: 0,
        msg: 'ok'
    }
})

// 开始服务并生成路由
app.use(router.routes())
   .use(router.allowedMethods());
app.listen(3002);//端口事件监听
