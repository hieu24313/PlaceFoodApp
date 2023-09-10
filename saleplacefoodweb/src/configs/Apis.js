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
    "detail": (foodId) => `${SERVER_CONTEXT}/api/foodItems/${foodId}/`,
    "comments": (foodId) => `${SERVER_CONTEXT}/api/foodItems/${foodId}/comments/`,
    "add-comment": `${SERVER_CONTEXT}/api/add-comment/`,
    "restaurant": `${SERVER_CONTEXT}/api/restaurants/`,
    "restaurant_detail": (restaurantId) => `${SERVER_CONTEXT}/api/restaurants/${restaurantId}/`,
    "register-restaurant": `${SERVER_CONTEXT}/api/register-restaurant/`,
    "receipt": `${SERVER_CONTEXT}/api/receipts/`,
    "receiptDetail": (receiptId) => `${SERVER_CONTEXT}/api/receipt/${receiptId}/receiptDetails/`,
    "changePassword": `${SERVER_CONTEXT}/api/change-password/`,
    "follow": `${SERVER_CONTEXT}/api/follow/`,
    "check-follow": `${SERVER_CONTEXT}/api/check-follow/`,
    "check-comment": (foodId) => `${SERVER_CONTEXT}/api/foodItems/${foodId}/check-comment/`,
    "accept-receipt": (receiptId) => `${SERVER_CONTEXT}/api/receipt/${receiptId}/acceiptReceipt/`



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

