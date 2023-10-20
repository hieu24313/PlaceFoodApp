import { Button, Table, ToastContainer } from "react-bootstrap"
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent"
import { useEffect, useState } from "react"
import cookie from "react-cookies";
import { authApi, endpoints } from "../../configs/Apis";
import Moment from "react-moment";

const NowReceipt = () => {
    const restaurantId = cookie.load("restaurant");
    const [nowReceipt, setNowReceipt] = useState([]);




    const formatNumberWithCommas = (number) => {
        if (number !== null && number !== undefined) {
            return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }
    }

    useEffect(() => {
        const loadNowReceipt = async () => {
            try {
                let e = `${endpoints['get-Now-Receipt']}?restaurantId=${restaurantId}`;
                console.log(e);
                let { data } = await authApi().get(e);
                setNowReceipt(data);
                console.log(data);
            } catch (e) {
                console.log(e);
            }
        }

        loadNowReceipt();
    }, [])



    return <>
        <div className="dasboard_big">

            <div className="dasboard_1">
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                <ToastContainer />
                <h1 className="text-center text-muted">Đơn Hàng Hiện Tại</h1>
                <div style={{ marginLeft: '5px', textAlign: 'center'}}>
                    <Table striped bordered hover size="sm">
                        <thead>
                            <tr>
                                <th>Mã Hóa Đơn</th>
                                <th>Tên Món</th>
                                <th>Giá 1 món</th>
                                <th>Số Lượng</th>
                                <th>Tổng</th>
                                <th>Tên Người Nhận</th>
                                <th>Địa Chỉ Nhận</th>
                                <th>Số Điện Thoại</th>
                                <th>Ngày Tạo</th>
                            </tr>
                        </thead>
                        <tbody>
                            {nowReceipt.map(n => {
                                return <tr>
                                    <td>{n[1].receiptId}</td>
                                    <td>{n[0].foodName}</td>
                                    <td>{n[2].unitPrice}</td>
                                    <td>{n[2].quantity}</td>
                                    <td>{formatNumberWithCommas(n[2].quantity * n[2].unitPrice)}</td>
                                    <td>{n[1].userId.firstname} {n[1].userId.lastname}</td>
                                    <td>{n[1].userId.location}</td>
                                    <td>{n[1].userId.phonenumber}</td>
                                    <td><Moment format="DD/MM/YYYY hh:mm:ss" locale="vi">{n[1].receiptDate}</Moment>s</td>
                                </tr>
                            })}

                        </tbody>
                    </Table>
                </div>
            </div>
        </div>
    </>
}

export default NowReceipt;