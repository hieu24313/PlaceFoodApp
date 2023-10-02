import { useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent";

const PromotionManager = () => {
    const { restaurantId } = useParams();
    
    return<>
        <div className="dasboard_big">
            <div className="dasboard_1">
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                <h1 className="text-center">Trang quản lý khuyễn mãi {restaurantId}</h1>
            </div>
        </div>
    </>
}
export default PromotionManager;