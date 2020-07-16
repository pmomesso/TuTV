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
            '#3cb44b', '#469990', '#aaffc3', '#42d4f4', '#4363d8',
            '#000075', '#911eb4', '#f032e6', '#e6beff', '#800000',
            '#e6194b', '#f58231', '#ffd8b1', '#ffe119', '#bfef45'
        ];

        let label = [];
        let data = [];
        let color = [];

        this.state.dataSet.forEach((dataRow, i) => {
            label.push(dataRow.label);
            data.push(dataRow.value);
            color.push(colors[i]);
        });
        
        return {
            labels: label,
            datasets: [{
                data: data,
                backgroundColor: color,
                hoverBackgroundColor: color
            }]
        };
    };

    render() {
        const options = {
            legend: {
                display: true,
                position: 'bottom',
                labels: {
                    padding: 20
                }
            }
        };

        return (
            <Doughnut data={ this.getData } options={ options } />
        );
    }
}

export default DoughnutChart;