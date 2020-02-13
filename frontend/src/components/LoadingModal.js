import React from "react";
import { Modal } from "react-bootstrap";
import { Spinner } from 'react-activity';
import 'react-activity/dist/react-activity.css';

const LoadingModal = (props) => {
    return (
        <Modal size="sm" backdrop="static" centered show={props.visible}>
            <Modal.Body>
                <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                    <Spinner color="#727981" size={32} speed={1} animating={true} />
                </div>
            </Modal.Body>
        </Modal>
    );
}

export default LoadingModal;