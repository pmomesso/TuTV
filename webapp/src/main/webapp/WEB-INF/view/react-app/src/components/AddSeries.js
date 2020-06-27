import React, {Component} from 'react';

class AddSeries extends Component {
    state = {
        name: '',
        posterUrl: ''
    }

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        });
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.addSeries(this.state);
    }

    render() {
        return (
            <div>
                <form onSubmit={ this.handleSubmit }>
                    <label htmlFor="name">Name: </label>
                    <input 
                        type="text"
                        id="name" 
                        value={ this.state.name }
                        onChange={ this.handleChange }/>

                    <label htmlFor="posterUrl">PosterUrl: </label>
                    <input 
                        type="text"
                        id="posterUrl" 
                        value={ this.state.posterUrl }
                        onChange={ this.handleChange }/>
                    
                    <button>Submit</button>
                </form>
            </div>
        )
    }
}

export default AddSeries;