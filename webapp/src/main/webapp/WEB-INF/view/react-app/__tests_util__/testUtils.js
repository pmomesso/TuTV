import React from 'react'
import { render as rtlRender } from '@testing-library/react'
import { createStore } from 'redux'
import { Provider } from 'react-redux'
import { BrowserRouter } from "react-router-dom";

const initState = {
    "auth": null
};

function log_user(state = initState) {
    return {
        ...state,
        auth: {"user": {"admin": true, "avatar": "", "banned": false, "id": 2, "userName": "User 2"}}
    }
}

function render(
    ui,
    {
        store = createStore(log_user),
        ...renderOptions
    } = {}
) {
    function Wrapper({ children }) {
        return <BrowserRouter><Provider store={store}>{children}</Provider></BrowserRouter>
    }
    return rtlRender(ui, { wrapper: Wrapper, ...renderOptions })
}

// re-export everything
export * from '@testing-library/react'
// override render method
export { render }