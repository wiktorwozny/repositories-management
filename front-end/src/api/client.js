/// create and export axios client
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";

const client = axios.create({
  withCredentials: true,
  baseURL: "http://localhost:8080",
  headers: {
    "Content-type": "application/json",
    "Access-Control-Allow-Credentials": true,
  },
});

function AxiosInterceptor({ children }) {
    const [isSet, setIsSet] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const interceptor = client.interceptors.response.use(
            response => {
                return response;
            },
            error => {
                if (error.response.status === 401) {
                    console.log("401")
                    navigate("/login");
                }
                return Promise.reject(error);
            }
        )

        setIsSet(true);

        return () => client.interceptors.response.eject(interceptor);
    }, [navigate]);

    return isSet && children;
}

export default client;
export { AxiosInterceptor };