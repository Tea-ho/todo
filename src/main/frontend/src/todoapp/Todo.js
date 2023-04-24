import React, { useState, useEffect }  from 'react';
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from '@mui/material';
import DeleteOutlined from "@mui/icons-material/DeleteOutlined";
import Axios from 'axios';

export default function Todo( props ) {
        // console.log(location.origin);
        console.log(props);

    const [ item, setItem ] = useState( props.item );
    // 해석1: item필드와 setItem Setter 생성
    // 해석2: useState가 생성자 역할 담당(초기화 진행)
    // 해석3: props로 들어온 매개변수의 item 값으로 초기화 진행 (props AppTodo.js로부터 받음 / TodoItems 참고)

    // -------------------------------- delete part --------------------------------
    // AppTodo.js에 생성한 삭제함수 변수로 받기
    const deleteItem = props.deleteItem;

    // deleteEventHandler 메소드 작성 (기능: 클릭한 item을 deleteItem 메소드의 매개변수로 전달)
    const deleteEventHandler = ()=>{
        deleteItem(item);
    }

    // -------------------------------- edit part --------------------------------
    const [readOnly, setReadOnly ] = useState(true);
    // 해석1: readOnly setReadOnly Setter 생성
    // 해석2: useState가 생성자 역할 담당(초기화 진행, 초기값: true)

    // turnOffReadOnly 메소드 작성 (기능: 읽기모드 해제처리(수정가능))
    const turnOffReadOnly = () => {
        setReadOnly(false); // readOnly = true(수정불가), false(수정가능)
    }

    // turnOnReadOnly 메소드 작성 (기능: Enter Key 작동 시, 읽기모드로 전환(수정 불가))
    const turnOnRead = (e) => {
        if(e.key === "Enter"){
            setReadOnly(true);
            axios.put("http://localhost:8080/todo", item).then(r=> {editItem();});
        }
    }

    // AppTodo.js에 생성한 수정함수 변수로 받기
    const editItem = props.editItem;
    const editEventHandler = (e) => { // editEventHandler 생성(기능: 제목 수정)
            console.log(e);
        item.title = e.target.value;
        editItem();
    }
    const checkboxEventHandler = (e) => { // checkboxEventHandler 생성(기능: 체크 박스 상태 변화)
        item.done = e.target.checked;
        axios.put("http://localhost:8080/todo", item).then(r=>{ editItem();
        })

    }

    // -------------------------------- return part --------------------------------
    return(<>
        <ListItem>

            <Checkbox checked={item.done} />
            <ListItemText>
                <InputBase inputProps = {{readOnly:readOnly}}
                    onClick={turnOffReadOnly}
                    onKeyDown={turnOnRead}
                    onChange={editEventHandler}
                    type="text"
                    id={ item.id }
                    name={ item.id }
                    value={ item.title }
                    multiline={ true }
                    fullWidth={ true }
                />
            </ListItemText>

            <ListItemSecondaryAction>
                <IconButton onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>

        </ListItem>
    </>);
    // 출력 내용: ListItem 구역 안에 Checkbox, Text 정보, Delete 버튼 (MUI 사용함)
}