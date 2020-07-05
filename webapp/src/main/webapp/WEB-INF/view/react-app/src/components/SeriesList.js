import React, {Component} from 'react';
import TvSeriesPoster from './TvSeriesPoster'
import { Trans } from 'react-i18next';
import Axios from 'axios';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';

class SeriesList extends Component {

  state = {
    loading: true,
    showList: [],
    areMore: false
  }

  componentDidMount = () => {
    let source = this.props.source;

    if(typeof source === "string") {
      Axios.get(source)
        .then(res => {
          console.log(res.headers);
            this.setState({
              showList: res.data.series,
              areMore: res.data.areNext,
              loading: false
            })
        });
    } else if(Array.isArray(source)) {
      this.setState({
        showList: source,
        areMore: false,
        loading: false
      })
    }
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
        <TvSeriesPoster key={ series.id } series={ series }/>
      )
    });
    
    return (
      <section>
        <h2 className="black-font">
          <Trans i18nKey={ "genres." + this.props.name }/>
        </h2>
        <ul className="posters-list shows-list explore-list list-unstyled list-inline overflow-hidden">
          { showList }
        </ul>
      </section>
    );
  }
}

export default SeriesList;
