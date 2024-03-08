/// create and export axios client
import axios from "axios";

///TODO: change the baseURL to the actual backend URL
export default axios.create({
  baseURL: "http://localhost:3000",
});
