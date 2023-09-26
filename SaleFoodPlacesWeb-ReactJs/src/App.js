import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import './App.css';
import Header from './layout/Header';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Container } from 'react-bootstrap';
import Footer from './layout/Footer';
import Home from './components/Home';
import Register from './components/Register';
import { createContext, useReducer } from 'react';
import Login from './components/Login';
import MyUserReducer from './reducers/MyUserReducer';
import MyCartCounterReducer from './reducers/MyCartCounterReducer';
import cookie from "react-cookies";
import Cart from './components/Cart';
import Profile from './components/Profile';
import FoodItemDetail from './components/FoodItemDetail';
import Restaurant from './components/Restaurant';
import RestaurantDetail from './components/RestaurantDetail';
import RegisterRestaurant from './components/RegisterRestaurant';
import Receipt from './components/Receipt';
import ChangePassword from './components/ChangePassword';
import RestaurantManager from './components/RestaurantManager';


export const MyUserContext = createContext();
export const MyCartContext = createContext();

const countCart = () => {
  let cart = cookie.load("cart") || null;
  if (cart !== null)
    return Object.values(cart).reduce((init, current) => init + current["quantity"], 0);
  return 0;
}

function App() {

  const [user, dispatch] = useReducer(MyUserReducer, cookie.load("user") || null);
  const [cartCounter, cartDispatch] = useReducer(MyCartCounterReducer, countCart());

  return (
    <MyUserContext.Provider value={[user, dispatch]}>
      <MyCartContext.Provider value={[cartCounter, cartDispatch]}>
        <BrowserRouter>
          <Header />
          <Container className="contain">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/cart" element={<Cart />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/fooddetail/:foodId" element={<FoodItemDetail />} />
              <Route path="/restaurant" element={<Restaurant />} />
              <Route path="/restaurant_detail/:restaurantId" element={<RestaurantDetail />} />
              <Route path="/register_restaurant" element={<RegisterRestaurant />} />
              <Route path="/receipt" element={<Receipt />} />    
              <Route path="/changepassword" element={<ChangePassword />} />  
              <Route path="/restaurantmanager" element={<RestaurantManager />} />
              </Routes>
          </Container>
          <Footer />
        </BrowserRouter>
      </MyCartContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;
