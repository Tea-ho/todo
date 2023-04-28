import React from 'react';
import "../index.css";
import AppTodo from './todo/AppTodo';
import Login from './member/Login';
import SignUp from './member/SignUp';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Typography, Box } from '@mui/material';

// router 연결 파일
function Copyright(props) {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            teoenginer, {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

function AppRouter() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route path="/todo/apptodo" element={<AppTodo/>} />
                    <Route path="/member/login" element={<Login/>} />
                    <Route path="/member/signup" element={<SignUp/>} />
                </Routes>
                <Box mt={5}>
                    <Copyright />
                </Box>
            </BrowserRouter>
        </div>
    )
}
export default AppRouter;