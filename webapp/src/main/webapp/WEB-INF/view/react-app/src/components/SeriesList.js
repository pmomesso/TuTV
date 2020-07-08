import React, {Component} from 'react';
import TvSeriesPoster from './TvSeriesPoster'
import { Trans } from 'react-i18next';
import Axios from 'axios';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import $ from "jquery";

let linkHeaderParser = require('parse-link-header');

class SeriesList extends Component {
  state = {
    source: null,
    loading: true,
    showList: [],
    prevUrl: null,
    nextUrl: null
  }

  nextPage = () => {
    this.setState({
      source: this.state.nextUrl,
      loading: true
    }, this.fetchData);
  }

  prevPage = () => {
    this.setState({
      source: this.state.prevUrl,
      loading: true
    }, this.fetchData);
  }

  fetchData = () => {
    let source = this.state.source;

    if(typeof source === "string") {
      var width = window.screen.width;
      var section_width = $("#explore section")[0].offsetWidth;
      var pagesize;
      if (width > 768) {
        pagesize = (width - 304 - section_width*0.04)/221;
      } else {
        pagesize = (width - 64 - section_width*0.04)/162;
      }
      Axios.get(source,  {params: {pagesize: pagesize.toFixed()}})
        .then(res => {
          let linkHeader = res.headers["link"];
          let linkHeaderParsed = linkHeaderParser(linkHeader);

          let nextUrl = null;
          let prevUrl = null;
          if(linkHeaderParsed) {
            nextUrl = linkHeaderParsed.next ? linkHeaderParsed.next.url : null;
            prevUrl = linkHeaderParsed.prev ? linkHeaderParsed.prev.url : null;
          }

            this.setState({
              showList: res.data.series,
              nextUrl: nextUrl,
              prevUrl: prevUrl,
              loading: false
            })
        });
    } else if(Array.isArray(source)) {
      this.setState({
        showList: source,
        loading: false
      })
    }
  }

  componentDidMount = () => {
    this.setState({ source: this.props.source }, this.fetchData);
  }

  render() {

    if(this.state.loading)
      return (
        <section>
          <h2 className="black-font">
            <Trans i18nKey={ "genres." + this.props.name }/>
          </h2>
          <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
            <Digital color="#727981" size={32} speed={1} animating={true} />
          </div>
        </section>
      );


    const showList = this.state.showList.map(series => {
      return(
        <TvSeriesPoster key={ series.id } series={ series } onSeriesFollowClickedHandler={this.props.onSeriesFollowClickedHandler}/>
      )
    });
    
    return (
      <section>
        <h2 className="black-font">
          <Trans i18nKey={ "genres." + this.props.name }/>
        </h2>
        <ul className="posters-list shows-list explore-list list-unstyled list-inline overflow-hidden">
        {(typeof this.state.prevUrl === "string") &&
            <a className="carousel-genre-left float-left" data-slide="prev" onClick={this.prevPage}>
              <span className="carousel-control-prev-icon my-prev-icon"></span>
            </a>
          }

          { showList }

          {(typeof this.state.nextUrl === "string") &&
            <a className="carousel-genre-right float-left" data-slide="next" onClick={this.nextPage}>
              <span className="carousel-control-next-icon my-next-icon"></span>
            </a>
          }
        </ul>
      </section>
    );
  }
}

export default SeriesList;
