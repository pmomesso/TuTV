import React, {Component} from 'react';
import { Trans } from 'react-i18next';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import { Link } from 'react-router-dom';
import Axios from 'axios';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';

class SeriesCarousel extends Component {

    state = {
        loading: true,
        showList: []
    }

    componentDidMount = () => {
        let source = this.props.source;

        if (typeof source === "string") {
            Axios.get(source)
                .then(res => {
                    this.setState({
                        showList: res.data,
                        loading: false
                    })
                });
        } else if (Array.isArray(source)) {
            this.setState({
                showList: source,
                loading: false
            })
        }
    }

    render() {
        if(this.state.loading)
            return (
                <section>
                    <h1>
                        <Trans i18nKey="index.newShows" />
                    </h1>
                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Digital color="#727981" size={32} speed={1} animating={true} />
                    </div>
                </section>
            );

        const indicators = this.state.showList.map((series, index) => {
            return (<li key={index} data-target="#myCarousel" data-slide-to={index} className={(index === 0 ? "active": "")}></li>);
        });

        const elements = this.state.showList.map((series, index) => {
            return (
                <div key={index} className={"carousel-item " + (index === 0 && "active")}>
                    <Link to={"/series/" + series.id}>
                        <img src={"https://image.tmdb.org/t/p/original" + series.bannerUrl} itemProp="image" alt="" />
                        <div className="carousel-caption">
                            <h2>{series.name}</h2>
                            <h3>
                                <Trans i18nKey="index.followers" count={series.followers} />
                            </h3>
                        </div>
                    </Link>
                </div>
            );
        });

        return (
            <section id="new-shows">
                <h1>
                    <Trans i18nKey="index.newShows" />
                </h1>
                <div id="myCarousel" className="carousel slide" data-ride="carousel">
                    <ol className="carousel-indicators">
                        {indicators}
                    </ol>

                    <div className="carousel-inner">
                        {elements}
                    </div>

                    <a className="carousel-control-prev" href="#myCarousel" data-slide="prev">
                        <span className="carousel-control-prev-icon"></span>
                    </a>
                    <a className="carousel-control-next" href="#myCarousel" data-slide="next">
                        <span className="carousel-control-next-icon"></span>
                    </a>
                </div>
            </section>
        );
    }

}

export default SeriesCarousel;