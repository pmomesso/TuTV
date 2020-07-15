import React, {Component} from 'react';
import {Doughnut} from 'react-chartjs-2';

class DoughnutChart extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            dataSet: props.dataSet ? props.dataSet : []
        };
      }

    getData = () => {
        const colors = [
            "#FF6384",
            "#36A2EB",
            "#FFCE56",
            "#4661EE",
            "#1BCDD1",
            "#8FAABB",
            "#B08BEB",
            "#3EA0DD",
            "#F5A52A",
            "#23BFAA",
            "#FAA586",
            "#EB8CC6",
            "#EC5657",
        ]

        let label = [];
        let data = [];
        let color = [];

        this.state.dataSet.forEach((dataRow, i) => {
            label.push(dataRow.label);
            data.push(dataRow.value);
            color.push(colors[i]);
        })
        
        return {
            labels: label,
            datasets: [{
                data: data,
                backgroundColor: color,
                hoverBackgroundColor: color
            }]
        };
    }

    render() {
        return (
            <Doughnut data={ this.getData } />
        );
    }
}

export default DoughnutChart;