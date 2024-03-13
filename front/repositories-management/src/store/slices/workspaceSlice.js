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
    // const response = await client.get("workspace");
    // return response.data;
    return [
      {
        id: 1,
        name: "Workspace 1",
        repositories: [
          {
            id: 1,
            name: "Repository 1",
            url: "https://github.com/VAST-AI-Research/TripoSR",
          },
        ],
      },
    ];
  },
);

export const addWorkspace = createAsyncThunk(
  "workspace/addWorkspace",
  async (workspace) => {
    // const response = await client.post("workspace", workspace);
    // return response.data;
    console.log("slice", workspace);
    return workspace;
  },
);

export const editWorkspace = createAsyncThunk(
  "workspace/editWorkspace",
  async (workspace) => {
    // const response = await client.put("workspace", workspace);
    // return response.data;
    return workspace;
  },
);
export const deleteWorkspace = createAsyncThunk(
  "workspace/deleteWorkspace",
  async (workspace) => {
    // const response = await client.delete("workspace", workspace);
    // return response.data;
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
