import React from 'react';
import { Container, Grid, Typography, TextField, Button } from '@mui/material';
import { signup } from '../service/ApiService';
import { Link } from 'react-router-dom';

export default function SignUp( props ) {

    // handleSubmit 메소드 생성 (기능: username, password 데이터 받아와서 ApiService 컴포넌트에 signin 메소드로 데이터 전송)
    const handleSubmit = ( event ) => {
        event.preventDefault();
        const data = new FormData( event.target );
        const username = data.get( 'username' );
        const password = data.get( 'password' );
        signup( {username: username, password: password} )
            .then( response => { // 정상적으로 회원가입 되면, login 페이지로 이동
                window.location.href = '/member/login';
            });
    };

    return (
        <Container component="main" maxWidth="xs" style={{marginTop:"8%"}}>
            <form noValidate onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <Typography component="h1" variant="h5">
                            계정 생성
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            autoComplete="fname"
                            name="username"
                            variant="outlined"
                            required
                            fullWidth
                            id="username"
                            label="아이디"
                            autoFocus
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            autoComplete="current-password"
                            name="password"
                            variant="outlined"
                            required
                            fullWidth
                            id="password"
                            type="password"
                            label="패스워드"
                            autoFocus
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                        >
                            계정 생성
                        </Button>
                    </Grid>
                </Grid>
                <Grid container justify="flex-end">
                    <Grid item>
                        <Link to="/member/login" variant="body2">
                            이미 계정이 있나요? 로그인하세요.
                        </Link>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );
};