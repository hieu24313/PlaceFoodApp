import { useContext, useState } from "react";
import { Alert, Button, Form, Table } from "react-bootstrap";
import cookie from "react-cookies";
import { Link, useNavigate } from "react-router-dom";
import { MyCartContext } from "../App";
import Apis, { authApi, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import '../resources/css/Cart.css'
import { toast } from "react-toastify";

const Cart = () => {

    const [loading, setLoading] = useState(false);
    const [carts, setCarts] = useState(cookie.load("cart") || null);
    const [user,] = useState(cookie.load("user") || null);
    const [, cartDispatch] = useContext(MyCartContext);
    const nav = useNavigate();
    const [checkUser, setCheckUser] = useState();
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
            try{
                if (user == null) {

                    let res = await Apis.post(endpoints['payNoUser'], carts);
                    if (res.status === 200) {
                        console.log(carts);
                        cookie.remove("cart");
    
                        cartDispatch({
                            "type": "update",
                            "payload": 0
                        });
    
                        setCarts([]);
                        setLoading(false);
                        toast.success('Thanh toán thành công!')
                        setTimeout(() => {
                            nav("/") 
                        }, 1000);
                    }
                    else {
                        toast.error("thanh toán thất bại!!!");
                    }
                } else {
                    let res = await authApi().post(endpoints['pay'], carts);
                    if (res.status === 200) {
                        console.log(carts);
                        cookie.remove("cart");
    
                        cartDispatch({
                            "type": "update",
                            "payload": 0
                        });
    
                        setCarts([]);
                        nav("/receipt");
                    }else {
                        toast.error("thanh toán thất bại!!!");
                    }
                }
            }catch(e){
                setLoading(false);
                console.log(e);
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

    // if (user === null){
    //     setCheckUser(false);
    // }

    // if (carts.length === 0)
    //     nav("/receipt");

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
                                    onChange={(e) => setCarts({ ...carts, [c.foodId]: { ...carts[c.foodId], "quantity": parseInt(regex.test(e.target.value) === true ? e.target.value : 0) } })} />
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
             {user !== null ? <>
                {loading === true ? <MySpinner /> : <Button variant="success" onClick={pay} className="mt-2 mb-2 btn_pay">Thanh toán</Button>}
            </> : <>
                {loading === true ? <MySpinner /> : <Button variant="success" onClick={pay} className="mt-2 mb-2 btn_pay">Thanh toán</Button> }
                
            </>}
            
        </div>
    </>
}
export default Cart;