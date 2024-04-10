import { configureStore } from "@reduxjs/toolkit";

import workspaceReducer from "./store/slices/workspaceSlice";

const store = configureStore({
  reducer: {
    // Define a top-level state field named `todos`, handled by `todosReducer`
    workspace: workspaceReducer,
  },
});

export default store;
