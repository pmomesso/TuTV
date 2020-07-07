import React, {Component} from 'react';
import { withTranslation } from 'react-i18next';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import { Link } from 'react-router-dom';
import Axios from 'axios';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import Carousel from 'react-bootstrap/Carousel'

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

        const { t } = this.props;

        if(this.state.loading)
            return (
                <section>
                    <h1>
                        { t("index.newShows") }
                    </h1>
                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Digital color="#727981" size={32} speed={1} animating={true} />
                    </div>
                </section>
            );

        const elements = this.state.showList.map((series, index) => {
            return (
                <Carousel.Item key={series.id}>
                    <Link to={"/series/" + series.id}>
                        <img src={"https://image.tmdb.org/t/p/original" + series.bannerUrl} itemProp="image" alt="" />
                        <Carousel.Caption>
                            <h2>{series.name}</h2>
                            <h3>
                                { t("index.followers", { count: series.followers }) }
                            </h3>
                        </Carousel.Caption>
                    </Link>
                </Carousel.Item>
            );
        });

        return (
            <section id="new-shows">
                <h1>
                    { t("index.newShows") }
                </h1>
                <Carousel>
                    {elements}
                </Carousel>
            </section>
        );
    }

}

export default withTranslation()(SeriesCarousel);