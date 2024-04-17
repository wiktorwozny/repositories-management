import { useState } from "react";
import client from "../../api/client";
import styled from "styled-components";
import { Button } from "@mui/material";
import {useNavigate} from "react-router-dom";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
    max-width: 300px;
    margin: 0 auto;
    padding: 24px;
    border: 1px solid #ddd;
    border-radius: 8px;
`;

const Form = styled.form`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    width: 100%;
    max-width: 300px;
    margin: 0 auto;
`;

const Input = styled.input`
    font-size: 16px;
    padding: 4px;
`;

const buttonStyle = {
    "marginTop": "16px"
}

function SignupForm() {
    const navigate = useNavigate();
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            await client.post("/api/auth/signup", {
                "login": login,
                "password": password,
            });

            navigate("/login");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <Wrapper>
            <h1>Sign up</h1>
            <Form onSubmit={handleSubmit}>
                <Input
                    type="text"
                    placeholder="Login"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                    autoComplete="on"
                    required
                />
                <Input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="on"
                    required
                />
                <Button variant="outlined" sx={buttonStyle} type="submit">
                    Sign up
                </Button>
            </Form>
        </Wrapper>
    );
}

export default SignupForm;