import {connect, useDispatch} from "react-redux";

import React, { useState } from "react";
import styled from "styled-components";
import { Button } from "@mui/material";

import LoginForm from "../components/authentication/LoginForm";
import SignupForm from "../components/authentication/SignupForm";
import {useNavigate} from "react-router-dom";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
`;

const TopBar = styled.div`
  display: flex;
  justify-content: space-between;
  width: 90%;
  padding: 1rem;
`;

const Controls = styled.div`
    display: flex;
    gap: 1rem;
    align-items: center;
`;

const Form = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
  width: 100%;
  align-items: center;
  flex-direction: column;
`;

function Authentication({ isSignedUp }) {
    const navigate = useNavigate();

    const handleSignedUp = () => {
        navigate("/login");
    };

    const handleNotSignedUp = () => {
        navigate("/signup");
    };

    return (
        <Wrapper>
            <TopBar>
                <h1>RepoManager</h1>
                <Controls>
                    <Button variant="outlined" onClick={handleSignedUp}>
                        Log in
                    </Button>
                    <Button variant="outlined" onClick={handleNotSignedUp}>
                        Sign up
                    </Button>
                </Controls>
            </TopBar>

            <Form>
                {isSignedUp ? (
                    <LoginForm />
                ) : (
                    <SignupForm />
                )}
            </Form>
        </Wrapper>
    );
}

export default Authentication;