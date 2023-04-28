import React, { useState } from 'react';
import { Button, Grid, TextField } from '@mui/material';

export default function AddTodo( props ) {

    // 1. 상태 변수 선언 (item 관리)
    const [ item, setItem ] = useState({title: ""}, {content: ""});
    // 해석1: item필드와 setItem Setter 생성
    // 해석2: useState가 생성자 역할 담당(초기화)
    // 해석3: item 필드 안에 title과 content 변수 생성한 후, 공백으로 초기화 진행
        // console.log(useState); // --- 확인 완료

    // 2. onInputChange 메소드 생성(기능: 입력창에 입력된 값 추출)
    const onInputChange = (e) => {
            // console.log(e);
            // console.log(e.target);
        setItem({title: e.target.value});
            console.log(item);
    }

    const addItem = props.addItem;
    // 해석: 필드 생성 및 초기화 (props로 들어온 정보 중 addItem으로 초기화 진행)

    // 3. onButtonClick 메소드 생성(기능: addItem메소드에 매개변수로 item 전달 후 title값 공백으로 초기화)
    const onButtonClick = ()=>{
        addItem(item);
        setItem({title:""})
    }

    // 4. enterKeyEventHandler 메소드 생성 (기능: enter key 눌리면, onButtonClick 메소드 작동)
    const enterKeyEventHandler = (e) => {
        if(e.key==='Enter'){
            onButtonClick();
        }
    }

    // -------------------------------- return part --------------------------------
    return(<>
        <Grid container style={{marginTop:20}}>
           <Grid xs={11} md={11} item style={{paddingRight:16}}>
                <TextField
                    placeholder="Todo 추가할 내용"
                    fullWidth
                    onChange={onInputChange}
                    onKeyPress={enterKeyEventHandler}
                    value={item.title}
                />
           </Grid>
            <Grid xs={1} md={1} item>
                <Button
                    fullWidth
                    style ={{height:'100%'}}
                    color="secondary"
                    variant="outlined"
                    onClick={onButtonClick}
                >
                +
                </Button>
            </Grid>
        </Grid>
    </>);
    // 출력 내용: 텍스트 입력란, + 버튼 (MUI 사용함)
}