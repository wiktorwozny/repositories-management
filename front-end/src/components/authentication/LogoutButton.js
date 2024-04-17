import client from "../../api/client";
import {Button} from "@mui/material";
import {useNavigate} from "react-router-dom";

function LogoutButton() {
    const navigate = useNavigate();

    const handleLogout = () => {
        try {
            client.post("/api/auth/logout");

            navigate("/login");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <Button variant="outlined" onClick={handleLogout}>Logout</Button>
    );
}

export default LogoutButton;