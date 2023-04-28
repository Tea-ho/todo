import React from 'react';
import { Container, Grid, Typography, TextField, Button } from '@mui/material';
import { signin } from '../service/ApiService';
import { Link } from 'react-router-dom';

export default function Login( props ) {

    // handleSubmit 메소드 생성 (기능: username, password 데이터 받아와서 ApiService 컴포넌트에 signin 메소드로 데이터 전송)
    const handleSubmit = (e) => {
        e.preventDefault();
        const data = new FormData(e.target);
        const username = data.get("username");
        const password = data.get("password");
        signin({username:username, password:password});
    };

    return(
      <Container component="main" maxWidth="xs" style={{marginTop: "8%"}}>
        <Grid container spacing={2}>
            <Grid item xs={12}>
                <Typography variant="h5" component="h1">
                  로그인
                </Typography>
            </Grid>
        </Grid>

        <form noValidate onSubmit={handleSubmit}>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <TextField
                        variant="outlined"
                        required
                        fullWidth
                        id="username"
                        label="아이디"
                        name="username"
                        autoComplete="username"
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        variant="outlined"
                        required
                        fullWidth
                        id="password"
                        label="패스워드"
                        name="password"
                        autoComplete="current-password"
                    />
                </Grid>
                <Grid item xs={12}>
                    <Button type="submit" fullWidth variant="contained" color="primary">
                        로그인
                    </Button>
                </Grid>
                <Grid item>
                    <Link to="/member/signup" variant="body2">
                        계정이 없나요? 여기서 가입하세요.
                    </Link>
                </Grid>
            </Grid>
        </form>
      </Container>
    )
}
