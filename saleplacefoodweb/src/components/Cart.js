import { useContext, useState } from "react";
import { Alert, Button, Form, Table } from "react-bootstrap";
import cookie from "react-cookies";
import { Link, useNavigate } from "react-router-dom";
import { MyCartContext } from "../App";
import { authApi, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import '../resources/css/Cart.css'

const Cart = () => {

    const [loading, setLoading] = useState(false);
    const [carts, setCarts] = useState(cookie.load("cart") || null);
    const [user,] = useState(cookie.load("user") || null);
    const [, cartDispatch] = useContext(MyCartContext);
    const nav = useNavigate();
    // const [empty, setEmpty] = useState();

    let tong = 0;

    const updateItem = () => {
        cookie.save("cart", carts);

        cartDispatch({
            "type": "update",
            "payload": Object.values(carts).reduce((init, current) => init + current["quantity"], 0)
        })
    }

    const deleteItem = (item) => {
        cartDispatch({
            "type": "dec",
            "payload": item.quantity
        });

        if (item.foodId in carts) {
            setCarts(current => {
                delete current[item.foodId];
                cookie.save("cart", current);

                return current;
            });
        }
    }

    const pay = () => {
        const process = async () => {
            setLoading(true);
            let res = await authApi().post(endpoints['pay'], carts);
            if (res.status === 200) {
                console.log(carts);
                cookie.remove("cart");

                cartDispatch({
                    "type": "update",
                    "payload": 0
                });

                setCarts([]);
            }
            else{
                alert("thanh toán thất bại!!!")
            }
        }

        process();
    }

    // const updateQuantity = (e, id) => {
    //     if (e.target.value == null) {
    //         setCarts({ ...carts, [id]: { ...carts[id], "quantity": 0 } })
    //     }
    //     else {
            
    //     }
    // }

    let regex = /^\d+$/;
    
    if (carts === null)
        return <Alert variant="info" className="mt-2">Không có sản phẩm trong giỏ!</Alert>


    if (carts.length === 0)
        nav("/receipt");

    return <>
    <h1 className="text-center text-success">Giỏ Hàng</h1>
        <div className="cart_table_parent">
        <Table striped bordered hover className="cart_table">
            <thead className="table_header">
                <tr>
                    <th>#</th>
                    <th>Tên sản phẩm</th>
                    <th>Đơn giá</th>
                    <th>Tổng</th>
                    <th>Số lượng</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {Object.values(carts).map(c => {
                    tong += c.unitPrice * c.quantity;
                    return <tr>
                        <td>{c.foodId}</td>
                        <td>{c.foodName}</td>
                        <td>{c.unitPrice}</td>
                        <td>{c.unitPrice * c.quantity} VNĐ</td>
                        <td>{c.quantity} </td>
                        <td>
                            <Form.Control type="number" value={carts[c.foodId]["quantity"]} onBlur={updateItem}
                                onChange={(e) => setCarts({ ...carts, [c.foodId]: { ...carts[c.foodId], "quantity": parseInt(regex.test(e.target.value) === true ? e.target.value : 0)  } })} />
                        </td>
                        <td>
                            <Button variant="danger" onClick={() => deleteItem(c)}>Xóa</Button>
                        </td>
                    </tr>
                })}


            </tbody>
        </Table>
        </div>
        <div className="div_btn_pay">
            <h3>Tổng Tiền: {tong}</h3>
        </div>
        <div className="div_btn_pay">
        {user === null ? <p>Vui lòng <Link to="/login?next=/cart">đăng nhập</Link> để thanh toán! </p> : 
            user.location !== null  ? 
            <>
                {loading === true ? <MySpinner />: <Button variant="success" onClick={pay} className="mt-2 mb-2 btn_pay">Thanh toán</Button>} 
            </>
            : <p>Vui lòng thêm <Link to="/profile">địa chỉ</Link> để thanh toán!</p>}
        </div>
    </>
}
export default Cart;