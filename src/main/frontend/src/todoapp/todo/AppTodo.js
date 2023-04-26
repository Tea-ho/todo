import React, { useState, useEffect } from 'react';
import Todo from './Todo';
import { Container, List, Paper, Grid, Button, AppBar, Toolbar, Typography } from '@mui/material';
import AddTodo from './AddTodo';
import { call, signout } from '../service/ApiService';
// import axios from 'axios'; // npm install axios

export default function AppTodo( props ) {

    const [items, setItems] = useState(
        [
        ]
    )
    // 해석1: items필드와 setItems Setter 생성
    // 해석2: useState가 생성자 역할 담당(초기화 진행)
    // 해석3: 등록되어 있는 item 데이터를 담기 위해 배열 형태로 생성 ([] 사용)

    // -------------------------------- Back end Server Connection part
    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json'},
        };
        fetch("http://localhost:8080/todo/list.do", requestOptions)
            .then(response => response.json())
            .then(
                (response)=>{ setItems(response.data); },
                (error) => { } );
    }, []);

    useEffect(()) => {
        call("/todo/list.do", "GET", null)
            .then((response)=> setItems(response.data));
    },[]);

    /*
    const getTodo = () => {
        axios.get("http://localhost:8080/todo/list.do")
            .then( r => {
                console.log(r);
                setItems(r.data);
                }
            );
    }
    useEffect(()=>{
        getTodo();
     }, [])
    */
    // -------------------------------- adding Item part --------------------------------
    // addItem 메소드 생성(기능: item을 인수로 받은 후 초기화 진행 후 배열에 추가)
    const addItem = (item) => {
        // item.id = "ID-"+items.length
        // item.done = false;
        // setItems([...items, item ]);
        // 해석: setItems([...기존배열, 값]); 기존 배열에 값 추가하는 문법
        // 특징: 실행될 때마다 재랜더링 진행

        call("/todo/create.do", "POST", item)
            .then((response)=> setItems(response.data));
    };

        //axios.post("http://localhost:8080/todo/create.do", item).then(r=>{getTodo();});
    }

    // -------------------------------- deleting Item part --------------------------------
    const deleteItem = (item) => {
            console.log(items);
            console.log(item);

        call("/todo/delete.do", "DELETE", item)
            .then((response)=> setItems(response.data));

        // const newItems = items.filter( e => e.id !== item.id );
        // 해석1: 신규 배열 newItems 선언
        // 해석2: items에 저장되어 있는 item의 id와 삭제할 id가 같지 않으면, item 반환
        // 해석3: 조건에 맞는 item으로 newItems에 저장 작업 반복 진행
        // setItems( [...newItems] );
        // 해석: setItems 이용하여 초기화 작업 진행

        // 배열/리스트.filter( (o) => {} );
        // 특징: map 문법에 조건 부여할 수 있음. 즉, 조건이 충족할 경우에만 객체를 return하도록 조작 가능!
        // JS 반복문 함수 모아보기
        // 1) forEach: 반복문 기능만 작동(return 반환 불가)
        // 2) map: 반복문 기능 수행하면서 return 반환처리 가능
        // 3) filter: 반복문 기능 수행하면서 조건부 return 반환 처리 가능

        // axios.delete("http://localhost:8080/todo/delete.do", {params: {tno: item.id}}).then(r=>{getTodo();});
    }

    // -------------------------------- editing Item part --------------------------------
    const editItem = (item)=>{
        // setItems([...items]);
        call("/todo/update.do", "PUT", item)
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

    // pagination 추가

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