import { useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent";
import { useContext, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/Apis";
import { Bar } from "react-chartjs-2";
import Chart from 'chart.js/auto';
import {CategoryScale} from 'chart.js'; 
Chart.register(CategoryScale);

const Revenus = () => {
  const { restaurantId } = useParams();
  const [statsRevenue, setStatsRevenue] = useState({ labels: [], data: [] });

  useEffect(() => {
    const loadStatsRevenue = async () => {
      try {
        console.log(restaurantId);
        let e = `${endpoints['get-stats-revenue']}?restaurantId=${restaurantId}`;
        let res = await authApi().get(e);

        const statsData = res.data;
        const labels = statsData.map((stat) => stat[1]);
        const data = statsData.map((stat) => stat[2]);

        setStatsRevenue({ labels, data });
        console.log(statsData);
      } catch (e) {
        console.log(e);
      }
    };
    loadStatsRevenue();
  }, [restaurantId]);

  const data1 = {
    labels: statsRevenue.labels,
    datasets: [
      {
        label: "Doanh thu (VND)",
        data: statsRevenue.data,
        backgroundColor: [
          "red",
          "blue",
          "yellow",
          "green",
          "purple",
          "orange",
        ],
      },
    ],
  };

  return (
    <div className="dasboard_big">
      <div className="dasboard_1">
        <RestaurantManagerConpoment />
      </div>
      <div className="dasboard_2">
        <h1 className="text-center">Trang chủ quản lý {restaurantId}</h1>
        <div id="RevenueChart" style={{width:'50%', margin: 'auto auto'}}>
        {statsRevenue.labels.length > 0 && statsRevenue.data.length > 0 ? (
          <Bar data={data1} />
        ) : (
          <p>Loading...</p>
        )}
        </div>
      </div>
    </div>
  );
};

export default Revenus;
