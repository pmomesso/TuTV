import React, {Component} from 'react';
import TvSeriesPoster from './TvSeriesPoster'
import Axios from 'axios';
import { Trans } from 'react-i18next';
import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import $ from "jquery";
import {NavLink} from "react-router-dom";

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
    let section = this.props.section;

    if(typeof source === "string") {
      var width = window.screen.width;
      var height = window.screen.height;

      var pagesize = 1;
      if (this.props.section === "#profile") {
        if (width > 375) pagesize = 2;
        if (width > 528) pagesize = 3;
        if (width > 680) pagesize = 4;
        if (width > 773) pagesize = 3;
        if (width > 920) pagesize = 4;
        if (width > 1075) pagesize = 5;
        if (width > 1230) pagesize = 6;
      } else {
        var section_width = $(section + " section")[0].offsetWidth;
        var poster_width = 187;

        if (width > 768)
          pagesize = (section_width - 24 - (section_width-24)*0.04)/poster_width;
        else
          pagesize = (section_width - section_width*0.04)/162;
      }
      if (section === "#search") {
        if (height > 750)
          pagesize *= 3;
        else
          pagesize *= 2;
      }
      Axios.get(source,  {params: {pagesize: Math.floor(pagesize)}})
        .then(res => {
          let linkHeader = res.headers["link"];
          let linkHeaderParsed = linkHeaderParser(linkHeader);

          let nextUrl = null;
          let prevUrl = null;
          if(linkHeaderParsed) {
            nextUrl = linkHeaderParsed.next ? linkHeaderParsed.next.url : null;
            prevUrl = linkHeaderParsed.prev ? linkHeaderParsed.prev.url : null;
          }

          const seriesElement = res.data.series ? res.data.series : res.data;

          this.setState({
            showList: seriesElement,
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
  };

  onRemoveFromListClickedHandler = () => {
    this.setState({ source: this.props.source }, this.fetchData);
  };

  render() {

    if(this.state.loading)
      return (
        <section>
          { (this.props.name) &&
            <h2 className="black-font">
              { this.props.name }
            </h2>
          }
          <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
            <Digital color="#727981" size={32} speed={1} animating={true} />
          </div>
        </section>
      );

    const { userLists, addSeriesToListHandler } = this.props;

    const showList = this.state.showList.map(series => {
      return(
        <TvSeriesPoster key={ series.id } series={ series } onSeriesFollowClickedHandler={this.props.onSeriesFollowClickedHandler} userLists={userLists} isLists={this.props.isLists} list={this.props.list} addSeriesToListHandler={addSeriesToListHandler} onRemoveFromListClickedHandler={this.onRemoveFromListClickedHandler}/>
      )
    });
    
    return (
        <div style={{minHeight: "30px"}}>
        {
          (this.state.showList.length) ?
            (<section>
              { (this.props.name) &&
              <h2 className="black-font">
                { this.props.name }
              </h2>
              }
              <ul className="posters-list shows-list explore-list list-unstyled list-inline overflow-hidden">
                {(typeof this.state.prevUrl === "string") &&
                <span className="clickable carousel-genre-left float-left" data-slide="prev" onClick={this.prevPage}>
                  <span className="carousel-control-prev-icon my-prev-icon"></span>
                </span>
                }

                { showList }

                {(typeof this.state.nextUrl === "string") &&
                <span className="clickable carousel-genre-right float-left" data-slide="next" onClick={this.nextPage}>
                  <span className="carousel-control-next-icon my-next-icon"></span>
                </span>
                }
              </ul>
            </section>)
            :
            (
              (this.props.section === "#search") ?
                  (<div key={1} style={{marginTop: "10%"}} className="container-center">
                    <h4><Trans i18nKey="search.noResults"/></h4>
                  </div>)
                  :
                  (
                    (this.props.isLists) ?
                        (<div key={1} style={{marginTop: "3%"}} className="container-center">
                          <h4><Trans i18nKey="profile.lists.noSeries"/></h4>
                        </div>)
                        :
                        (
                          (this.props.currUser) ?
                              (<div className="container h-100">
                                <div className="row justify-content-center h-100">
                                  <div className="col-lg-8 col-sm-12 align-self-center">
                                    <div className="text-center m-4">
                                      <h4>
                                        <Trans i18nKey="watchlist.discover" />
                                      </h4>
                                    </div>
                                    <div className="text-center m-4">
                                      <NavLink className="tutv-button m-4" id="menu_home" to="/">
                                        <Trans i18nKey="watchlist.explore" />
                                      </NavLink>
                                    </div>
                                  </div>
                                </div>
                              </div>)
                              :
                              (<div className="container h-100">
                                <div className="row justify-content-center h-100">
                                  <div className="col-lg-8 col-sm-12 align-self-center">
                                    <div className="text-center m-4">
                                      <h4>
                                        <Trans i18nKey="profile.userNoShows" values={{ user: this.props.user.userName.toUpperCase() }} />
                                      </h4>
                                    </div>
                                  </div>
                                </div>
                              </div>)
                        )
                  )
            )
        }
        </div>)
  }
}

export default SeriesList;
