import { useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent";
import { Alert, Button, Table } from "react-bootstrap";
import cookie, { load } from "react-cookies";
import { useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/Apis";
import MySpinner from "../../layout/MySpinner";
import Moment from "react-moment";

const PromotionManager = () => {
    const restaurantId = cookie.load("restaurant")
    const [promotion, setPromotion] = useState(null);

    useEffect(() => {
        const getPromotion = async () => {
            try {
                let { data } = await authApi().get(endpoints['get-promotion'], {
                    "restaurantId": restaurantId
                })

                setPromotion(data);
            } catch (e) {
                console.log(e);
            }
        }
        getPromotion();
    })

    


    return <>
        <div className="dasboard_big">
            <div className="dasboard_1">
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                <h1 className="text-center">Trang quản lý khuyến mãi {restaurantId}</h1>
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Từ Ngày</th>
                            <th>Đến Ngày</th>
                            <th>Giá</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {promotion === null ? <MySpinner /> : <>
                            {promotion.length === 0 ? <Alert className="alert-primary">Nhà Hàng Chưa Có Khuyến Mãi Nào!</Alert> : <>
                                {promotion.map(p => {
                                    return <tr>
                                        <td>{p.promotionId}</td>
                                        <td>{p.promotionName}</td>
                                        <td><Moment format="DD/MM/YYYY">{p.fromDate}</Moment></td>
                                        <td><Moment format="DD/MM/YYYY">{p.toDate}</Moment></td>
                                        {p.promotionTypeId.promotionTypeId === 1 ? <td>{p.pricePromotion} %</td> : <td>{p.pricePromotion} VNĐ</td>}
                                        <td><Button className="btn-danger">Xóa</Button></td>
                                    </tr>
                                })}
                            </>}
                        </>}
                    </tbody>
                </Table>
            </div>
        </div>
    </>
}
export default PromotionManager;