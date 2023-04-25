import React from 'react';
import { BrowserRouter , Routes , Route }
    from "react-router-dom" // npm install react-router-dom
import AppTodo from './todo/AppTodo'

export default function Index( props ) {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/todo/apptodo" element = { <AppTodo/> } />
            </Routes>
        </BrowserRouter>
    );
}