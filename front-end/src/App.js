import logo from "./logo.svg";
import "./App.css";
import { connect } from "react-redux";

import { useSelector, useDispatch } from "react-redux";
import { fetchWorkspaceList } from "./store/slices/workspaceSlice";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AxiosInterceptor } from "./api/client";
import Authentication from "./pages/Authentication";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
      <Router>
          <AxiosInterceptor>
              <Routes>
                  <Route path="/login" element={ <Authentication isSignedUp={ true } /> } />
                  <Route path="/signup" element={ <Authentication iSignedUp={ false } /> } />
                  <Route path="/dashboard" element={ <Dashboard /> } />
                  <Route path="*" element={ <Authentication signedUp={ true } /> } />
              </Routes>
          </AxiosInterceptor>
      </Router>
  );
}

export default connect()(App);
