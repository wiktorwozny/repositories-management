import {
  createAsyncThunk,
  createEntityAdapter,
  createSlice,
} from "@reduxjs/toolkit";
import client from "../../api/client";

const workspaceAdapter = createEntityAdapter();

// Thunk functions
export const fetchWorkspaceList = createAsyncThunk(
  "workspace/fetchWorkspaceList",
  async () => {
    const response = await client.get("/api/workspaces");
    return response.data;
  },
);

export const addWorkspace = createAsyncThunk(
  "workspace/addWorkspace",
  async (workspace) => {
    console.log("workspace", workspace);
    const response = await client.post("/api/workspaces", workspace.name);
    console.log("response", response.data);
    return response.data;
  },
);

export const editWorkspace = createAsyncThunk(
  "workspace/editWorkspace",
  async (workspace) => {
    const endpoint = "/api/workspaces/" + workspace.id;
    const response = await client.put(endpoint, {
      ...workspace,
    });
    return response.data;
  },
);
export const deleteWorkspace = createAsyncThunk(
  "workspace/deleteWorkspace",
  async (workspace) => {
    const endpoint = "/api/workspaces/" + workspace.id;
    await client.delete(endpoint);
    return workspace;
  },
);

const workspaceSlice = createSlice({
  name: "workspace",
  initialState: workspaceAdapter.getInitialState(),
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(fetchWorkspaceList.fulfilled, (state, action) => {
      state.workspaceList = action.payload;
    });
    builder.addCase(addWorkspace.fulfilled, (state, action) => {
      state.workspaceList.push(action.payload);
    });
    builder.addCase(editWorkspace.fulfilled, (state, action) => {
      const index = state.workspaceList.findIndex(
        (workspace) => workspace.id === action.payload.id,
      );
      state.workspaceList[index] = action.payload;
    });
    builder.addCase(deleteWorkspace.fulfilled, (state, action) => {
      state.workspaceList = state.workspaceList.filter(
        (workspace) => workspace.id !== action.payload.id,
      );
    });
  },
});

export default workspaceSlice.reducer;

export const { selectAll: selectAllWorkspace } = workspaceAdapter.getSelectors(
  (state) => state.workspace,
);
