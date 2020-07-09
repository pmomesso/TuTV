import React, {Component} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import { withTranslation } from 'react-i18next';

class Search extends Component {
    state = {
        pageSize: 60,
        genreList: [],
        networkList: [],
        searchGenre: undefined,
        searchNetwork: undefined,
        searchName: undefined
    };

    componentDidMount = () => {
        var that = this;

        Axios.all([
            Axios.get("/genres"),
            Axios.get("/networks"),
        ]).then(Axios.spread(function(genres, networks) {
            that.setState({
                ...that.state,
                genreList: genres.data,
                networkList: networks.data
            })
        }));  
    };

    encodeQueryParameter = (key, value) => {
        return encodeURIComponent(key) + "=" + encodeURIComponent(value) + "&";
    }

    composeSearchUrl = () => {
        const { pageSize, searchGenre, searchName, searchNetwork } = this.state;
        let queryParams = this.encodeQueryParameter("pagesize", pageSize);

        if(searchName)
            queryParams += this.encodeQueryParameter("name", searchName);

        if(searchGenre && searchGenre !== "0")
            queryParams += this.encodeQueryParameter("genre", searchGenre);

        if(searchNetwork && searchNetwork !== "0")
            queryParams += this.encodeQueryParameter("network", searchNetwork);

        return "/series?" + queryParams;
    };

    handleInputChange = (event) => {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
    
        this.setState({
            [name]: value
        });
    }

    render() {
        const { t } = this.props;

        const fetchUrl = this.composeSearchUrl();

        const genreSelect = this.state.genreList.map(genre => {
            return (<option key={genre.id} value={genre.id}>{ genre.name }</option>);
        });
        genreSelect.unshift(<option key="0" value="0"> { t("search.allGenres") } </option>)

        const networkSelect = this.state.networkList.map(network => {
            return (<option key={network.id} value={network.id}>{ network.name }</option>);
        });
        networkSelect.unshift(<option key="0" value="0"> { t("search.allNetworks") } </option>)

        return (
            <div className="alt-block" style={{background: 'white'}}>
                <div className="main-block">
                    <div className="main-block-container">
                        <div id="explore">
                            <input type="text" name="searchName" value={ this.state.seachName } onChange={this.handleInputChange}/>
                            <select name="searchGenre" value={ this.state.searchGenre } onChange={this.handleInputChange}>
                                { genreSelect }
                            </select>
                            <select name="searchNetwork" value={ this.state.searchNetwork } onChange={this.handleInputChange}>
                                { networkSelect }
                            </select>
                            
                            <SeriesList key={fetchUrl} source={ fetchUrl }/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withTranslation()(Search);