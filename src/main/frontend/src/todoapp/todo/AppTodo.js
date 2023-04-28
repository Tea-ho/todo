import React, { useState, useEffect } from 'react';
import Todo from './Todo';
import { Container, List, Paper, Grid, Button, AppBar, Toolbar, Typography } from '@mui/material';
import AddTodo from './AddTodo';
import { call, signout } from '../service/ApiService';
// import axios from 'axios'; // npm install axios

export default function AppTodo( props ) {

    // 1. 상태 변수 선언 (item 관리)
    const [items, setItems] = useState( [] )
    // 해석1: items필드와 setItems Setter 생성
    // 해석2: useState가 생성자 역할 담당(초기화 진행)
    // 해석3: 등록되어 있는 item 데이터를 담기 위해 배열 형태로 생성 ([] 사용)

    // 2. useEffet 사용하여 해당 컴포넌트가 처음 열렸을 때, spring에 연결되어있는 get method로 데이터 받아오기
    useEffect(() => {
        call("/todo", "GET", null)
            .then(
                (response)=>{ setItems(response.data) });
    }, []);

    // -------------------------------- adding Item part --------------------------------
    // 1. addItem 메소드 생성(기능: item을 인수로 받은 후 초기화 진행 후 배열에 추가)
    const addItem = (item) => {
        call("/todo", "POST", item)
            .then((response)=> setItems(response.data));
    };

    // -------------------------------- deleting Item part --------------------------------
    const deleteItem = (item) => {
            // console.log(items); // --- 확인 완료
            // console.log(item); // --- 확인 완료

        call("/todo", "DELETE", item)
            .then((response)=> setItems(response.data));
    }

    // -------------------------------- editing Item part --------------------------------
    const editItem = (item)=>{
        call("/todo", "PUT", item)
            .then((response)=> setItems(response.data));
    };

    // -------------------------------- creating TodoItems part --------------------------------
    let TodoItems =
            <Paper style={ {margin: 16} }>
                <List>
                    {
                        items.map( ( i ) =>
                            <Todo item = { i } key = {i.id} editItem={editItem} deleteItem={deleteItem} />
                        )
                    }
                </List>
            </Paper>
    // 해석1: TodoItems 필드 생성
    // 해석2: items index 처음부터 끝까지 반복문 작업 처리 진행
    // 해석3: 1회 반복 당 <Todo item = { i } key = {i.id} deleteItem={deleteItem} /> return 값으로 반환
    // 해석4: i의 id 등의 값은 위에 addItem 메소드를 통해 초기화 됨
    // 해석5: Todo.js에서 props로 item, key, deleteItem 3가지를 받고 있음. (필요에 맞게 Todo.js에서 분배하여 사용)

    // navigationBar 추가
    let navigationBar = (
        <AppBar position="static">
            <Toolbar>
               <Grid justifyContent="space-between" container>
                    <Grid item>
                        <Typography variant="h6"> Todo List </Typography>
                    </Grid>
               </Grid>
               <Grid item>
                    <Button color="inherit" onClick={signout}>
                        logout
                    </Button>
               </Grid>
            </Toolbar>
        </AppBar>
    );

    // -------------------------------- return part --------------------------------
    return(<>
        <div className="App">
            {navigationBar}
            <Container maxWidth="md">
                <AddTodo addItem={addItem} />
                <div className="TodoList">{ TodoItems }</div>
            </Container>

        </div>
    </>);
    // return 내부 AddTodo 부분 내용
    // 해석1: addTodo 컴포넌트에 addItem이름으로 addItem 메소드를 전달
    // 해석2: addTodo 컴포넌트에서는 props로 해당 메소드를 받음
}