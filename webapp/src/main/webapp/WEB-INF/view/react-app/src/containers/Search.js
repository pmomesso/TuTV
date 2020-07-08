import React, {Component} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import { withTranslation } from 'react-i18next';

class Search extends Component {
    state = {
        pageSize: 60,
        genreList: [],
        networkList: [],
        searchGenre: null,
        searchNetwork: null,
        searchName: null
    };

    componentDidMount = () => {
        var that = this;

        Axios.all([
            Axios.get("/series/genres"),
        ]).then(Axios.spread(function(genres) {
            that.setState({
                ...that.state,
                genreList: genres.data
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

        if(searchNetwork)
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
        const fetchUrl = this.composeSearchUrl();

        const genreSelect = this.state.genreList.map(genre => {
            return (<option key={genre.id} value={genre.id}>{ genre.name }</option>);
        });
        genreSelect.unshift(<option key="0" value="0"> Todos los géneros </option>)

        return (
            <div className="alt-block" style={{background: 'white'}}>
                <div className="main-block">
                    <div className="main-block-container">
                        <div id="explore">
                            <input type="text" name="searchName" value={ this.state.seachName } onChange={this.handleInputChange}/>
                            <select name="searchGenre" value={ this.state.searchGenre } onChange={this.handleInputChange}>
                                { genreSelect }
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