/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function drawRevenueChart(labels, data) {
const ctx = document.getElementById('RevenueChart');

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Doanh thu (VND)',
        data: data,
        borderWidth: 1,
        backgroundColor: ['red', 'green', 'blue', 'rgba(144, 180, 90, 0.8)', 'rgba(255, 180, 90, 0.8)']
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
}

function drawRevenueChartByCate(labels, data) {
const ctx = document.getElementById('RevenueChartFoodByCate');

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Doanh thu (VND)',
        data: data,
        borderWidth: 1,
        backgroundColor: ['red', 'green', 'blue', 'rgba(144, 180, 90, 0.8)', 'rgba(255, 180, 90, 0.8)']
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
}

function drawRevenueChartRestaurant(labels, data) {
const ctx = document.getElementById('RevenueChartRestaurant');

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Doanh thu (VND)',
        data: data,
        borderWidth: 1,
        backgroundColor: ['red', 'green', 'blue', 'rgba(144, 180, 90, 0.8)', 'rgba(255, 180, 90, 0.8)']
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
}