import axios from "axios";

const setJWTToken = (token) => {
  if (token) {
    axios.defaults.headers.common["Authorization"] = token;
    axios.defaults.headers.common["Content-Type"] = "application/json";
  } else {
    axios.defaults.headers.common["Content-Type"] = "application/json";
    delete axios.defaults.headers.common["Authorization"];
  }
};

export default setJWTToken;
