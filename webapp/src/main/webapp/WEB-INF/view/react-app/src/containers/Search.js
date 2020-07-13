import React, {PureComponent} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import { withTranslation } from 'react-i18next';
import { connect } from 'react-redux';
import { compose } from 'recompose';
import { withRouter } from 'react-router-dom';
import { store } from 'react-notifications-component';
import ErrorPage from "./ErrorPage";

class Search extends PureComponent {
    state = {
        genreList: [],
        networkList: [],
        searchGenre: undefined,
        searchNetwork: undefined,
        searchName: undefined,
        userLists: null,
        error: false
    };

    /*shouldComponentUpdate = (nextProps, nextState) => {
        if(this.props.location.state || nextProps.location.state) {
            if(!(this.props.location.state && nextProps.location.state))
                return true;

            return this.props.location.state.searchText !== nextProps.location.state.searchText;
        }

        return false;
    }*/

    componentDidMount = () => {
        var that = this;

        /*if(this.props.location.state && this.props.location.state.searchText) {
            this.setState({
                ...this.state,
                seachName: "assdwdqdwqdq"
            })
            this.props.history.replace({
                state: {}
            })
        }*/

        Axios.all([
            Axios.get("/genres"),
            Axios.get("/networks"),
        ]).then(Axios.spread(function(genres, networks) {
            that.setState({
                ...that.state,
                genreList: genres.data,
                networkList: networks.data
            })
        })).catch((err) => {
            const error_status = "error." + err.response.status + "status";
            const error_body = "error." + err.response.status + "body";
            this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
        });

        if(this.props.logged_user)
            Axios.get("/users/" + this.props.logged_user.id + "/lists")
            .then(res => {
                this.setState({
                    userLists: res.data,
                })
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    createSeriesListAndAddSeriesHandler = (series) => {
        const { t, logged_user } = this.props;
        let listName = window.prompt(t("series.newListName"),"");

        if(!listName) {
            store.addNotification({
                title: "Error",
                message: t("lists.noName"),
                type: "danger",
                insert: "top",
                container: "top-right",
                animationIn: ["animated", "fadeIn"],
                animationOut: ["animated", "fadeOut"],
                dismiss: {
                    duration: 4000,
                    onScreen: true
                }
            });
            return;
        }

        Axios.post("/users/" + logged_user.id + "/lists", JSON.stringify({"name": listName}))
            .then(res => {
                this.setState({
                    ...this.state,
                    userLists: [res.data].concat(this.state.userLists)
                });
                if (res.status === 200) {
                    store.addNotification({
                        title:  t("lists.success"),
                        message:  listName + t("lists.added"),
                        type: "success",
                        insert: "top",
                        container: "top-right",
                        animationIn: ["animated", "fadeIn"],
                        animationOut: ["animated", "fadeOut"],
                        dismiss: {
                            duration: 4000,
                            onScreen: true
                        }
                    });
                }
                this.addSeriesToListHandler(res.data, series);
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    addSeriesToListHandler = (list, series) => {
        const { t, logged_user } = this.props;

        if(!list) { //Crear lista
            this.createSeriesListAndAddSeriesHandler(series);
        } else {
            Axios.post("/users/" + logged_user.id + "/lists/" + list.id + "/series", JSON.stringify({"seriesId": series.id}))
                .then(res => {
                    if (res.status === 200) {
                        store.addNotification({
                            title:  t("lists.success"),
                            message:  series.name + t("lists.addedTo") + list.name,
                            type: "success",
                            insert: "top",
                            container: "top-right",
                            animationIn: ["animated", "fadeIn"],
                            animationOut: ["animated", "fadeOut"],
                            dismiss: {
                                duration: 4000,
                                onScreen: true
                            }
                        });
                    }
                })
                .catch(res => {
                    if (res.response.status === 304) {
                        store.addNotification({
                            title:  "Info",
                            message: series.name + t("lists.alreadyAdded") + list.name,
                            type: "info",
                            insert: "top",
                            container: "top-right",
                            animationIn: ["animated", "fadeIn"],
                            animationOut: ["animated", "fadeOut"],
                            dismiss: {
                                duration: 4000,
                                onScreen: true
                            }
                        });
                    }
                });
        }
    }

    encodeQueryParameter = (key, value) => {
        return encodeURIComponent(key) + "=" + encodeURIComponent(value) + "&";
    }

    composeSearchUrl = () => {
        const { searchGenre, searchNetwork } = this.state;

        const { search_name } = this.props;

        let queryParams = "";

        if(search_name)
            queryParams += this.encodeQueryParameter("name", search_name);

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

    handleSearchChange = (event) => {
        this.props.updateSearchName(event.target.value);
    }

    render() {
        if (!this.state.error) {
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
                            <div id="search">
                                <section>
                                    <h1>{ t("search.searchResults") }</h1>
                                    <div className="container">
                                        <div className="row">
                                            <div className="col sidebar-box">
                                                <input type="text" className="styled-input styled-select" name="searchName" placeholder={ t("search.search") } value={ this.props.search_name } onChange={this.handleSearchChange}/>
                                            </div>
                                            <div className="col sidebar-box">
                                                <select name="searchGenre" className="styled-select" value={ this.state.searchGenre } onChange={this.handleInputChange}>
                                                    { genreSelect }
                                                </select>
                                            </div>
                                            <div className="col sidebar-box">
                                                <select name="searchNetwork" className="styled-select" value={ this.state.searchNetwork } onChange={this.handleInputChange}>
                                                    { networkSelect }
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <SeriesList key={fetchUrl} source={ fetchUrl } section={"#search"} addSeriesToListHandler={this.addSeriesToListHandler} userLists={this.state.userLists}/>
                                </section>
                            </div>
                        </div>
                    </div>
                </div>
            );
        } else {
            return <ErrorPage status={this.state.error_status} body={this.state.error_body}/>
        }
    }
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user,
        search_name: state.search.searchName
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateSearchName: searchName => { dispatch({ type: "UPDATE_SEARCH", payload: { "searchName": searchName} }) }
    }
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
    withTranslation()
)(withRouter(Search));