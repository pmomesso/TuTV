import React from 'react';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

const Logout = (props) => {
    props.logoutUser();
    return <Redirect to="/" />
}

const mapDispatchToProps = (dispatch) => {
    return {
        logoutUser: () => { dispatch({ type: "LOGOUT" }) }
    }
  }
  
  export default connect(null, mapDispatchToProps)(Logout);