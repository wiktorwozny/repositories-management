import logo from "./logo.svg";
import "./App.css";
import { connect } from "react-redux";

import { useSelector, useDispatch } from "react-redux";
import { fetchWorkspaceList } from "./store/slices/workspaceSlice";
import Dashboard from "./pages/Dashboard";

function App() {
  return <Dashboard />;
}

export default connect()(App);
