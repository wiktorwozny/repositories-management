import { useState } from "react";
import client from "../../api/client";
import {Button} from "@mui/material";
import styled from "styled-components";
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

// const

function LoginForm() {
    const navigate = useNavigate();
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [remember, setRemember] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await client.post(`/api/auth/login?remember-me=${remember}`, {
                "login": login,
                "password": password
            });

            navigate("/dashboard");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <Wrapper>
            <h1>Log in</h1>
            <Form>
                <Input
                    type="text"
                    placeholder="Login"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                    autoComplete="true"
                    required
                />
                <Input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="true"
                    required
                />
                <div>
                    <input
                        type="checkbox"
                        id={"remember"}
                        onChange={(e) => setRemember(e.target.checked)}
                    />
                    <label htmlFor="remember">Remember me</label>
                </div>
                <Button variant="outlined" sx={buttonStyle} type="submit" onClick={handleSubmit}>
                    Login
                </Button>
            </Form>
        </Wrapper>
    );
}

export default LoginForm;