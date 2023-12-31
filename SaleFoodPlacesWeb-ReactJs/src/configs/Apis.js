import axios from "axios";
import cookie from "react-cookies";

const SERVER_CONTEXT = "/SpringFoodPlacesWeb";
const SERVER = "http://localhost:8080";

export const endpoints = {
    "fooditems": `${SERVER_CONTEXT}/api/foodItems/`,
    "categories": `${SERVER_CONTEXT}/api/categories/`,
    "register": `${SERVER_CONTEXT}/api/register/`,
    "update-user": `${SERVER_CONTEXT}/api/update-user/`,
    "login": `${SERVER_CONTEXT}/api/login/`,
    "login-google": `${SERVER_CONTEXT}/api/login-google/`,
    "current-user": `${SERVER_CONTEXT}/api/current-user/`, //lấy user hiện đang login
    "pay": `${SERVER_CONTEXT}/api/pay/`,
    "payNoUser": `${SERVER_CONTEXT}/api/payNoUser/`,
    "detail": (foodId) => `${SERVER_CONTEXT}/api/foodItems/${foodId}/`,
    "comments": (foodId) => `${SERVER_CONTEXT}/api/foodItems/${foodId}/comments/`,
    "add-comment": `${SERVER_CONTEXT}/api/add-comment/`,
    "restaurant": `${SERVER_CONTEXT}/api/restaurants/`,
    "restaurant-profile": `${SERVER_CONTEXT}/api/restaurants/userProfile/`,
    "restaurant_detail": (restaurantId) => `${SERVER_CONTEXT}/api/restaurants/${restaurantId}/`,
    "register-restaurant": `${SERVER_CONTEXT}/api/register-restaurant/`,
    "receipt": `${SERVER_CONTEXT}/api/receipts/`,
    "receiptDetail": (receiptId) => `${SERVER_CONTEXT}/api/receipt/${receiptId}/receiptDetails/`,
    "changePassword": `${SERVER_CONTEXT}/api/change-password/`,
    "follow": `${SERVER_CONTEXT}/api/follow/`,
    "check-follow": `${SERVER_CONTEXT}/api/check-follow/`,
    "check-comment": (foodId) => `${SERVER_CONTEXT}/api/foodItems/${foodId}/check-comment/`,
    "accept-receipt": (receiptId) => `${SERVER_CONTEXT}/api/receipt/${receiptId}/acceiptReceipt/`,
    "get-login-google-key": `${SERVER_CONTEXT}/api/loginGoogleKey/`,
    "get-recaptcha-key": `${SERVER_CONTEXT}/api/recaptchaKey/`,
    "count-restaurant": `${SERVER_CONTEXT}/api/countRestaurant/`,
    "restaurant-manager": `${SERVER_CONTEXT}/api/restaurantManagerForClient/`,
    "send-otp": `${SERVER_CONTEXT}/api/sendOTP/`,
    "check-otp": `${SERVER_CONTEXT}/api/check-otp/`,
    "check-phonenumber": `${SERVER_CONTEXT}/api/findAccountByPhoneNumber/`,
    "check-otp-and-set-password": `${SERVER_CONTEXT}/api/checkOTPAndChangPassword/`,
    "check-restaurant-user": `${SERVER_CONTEXT}/api/restaurantManager/check-restaurant-user/`,
    "fooditems-promotion": `${SERVER_CONTEXT}/api/fooditems-promotion/`,
    "add-or-update-food": `${SERVER_CONTEXT}/api/restaurantManager/add-or-update-fooditem/`,
    "add-or-update-category": `${SERVER_CONTEXT}/api/restaurantManager/add-or-update-category/`,
    "delete-cate": `${SERVER_CONTEXT}/api/restaurantManager/delete-categoriesFood/`,
    "get-promotion": `${SERVER_CONTEXT}/api/restaurantManager/promotions/`,
    "get-promotion-with-date": `${SERVER_CONTEXT}/api/restaurantManager/promotionsWithDate/`,
    "delete-promotion": (promotionId) => `${SERVER_CONTEXT}/api/restaurantManager/delete-promotion/${promotionId}`,
    "add-or-update-promotion": `${SERVER_CONTEXT}/api/restaurantManager/add-or-update-promotion/`,
    "get-uni-promotion": `${SERVER_CONTEXT}/api/restaurantManager/get-uni-promotion/`,
    "get-stats-revenue": `${SERVER_CONTEXT}/api/restaurantManager/statsRevenue/`,
    "get-stats-restaurant": `${SERVER_CONTEXT}/api/restaurantManager/getStatsRestaurant/`,
    "get-stats-by-category": `${SERVER_CONTEXT}/api/restaurantManager/getStatsRevenueByCate/`,
    "get-food-item-has-promotion": `${SERVER_CONTEXT}/api/foodItems-after-promotion/`,
    "get-Now-Receipt":  `${SERVER_CONTEXT}/api/restaurantManager/nowReceipt/`

}

export const authApi = () => {
    return axios.create({
        baseURL: SERVER,
        headers: {
            "Authorization": cookie.load("token")
        }
    })
}

export default axios.create({
    baseURL: SERVER
})

