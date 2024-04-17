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

export const addRepository = createAsyncThunk(
  "workspace/addRepository",
  async (repository) => {
    const response = await client.post(
      `/api/workspaces/${repository.workspaceId}/repositories`,
      repository,
    );
    return { workspaceId: repository.workspaceId, ...response.data };
  },
);

export const deleteRepository = createAsyncThunk(
  "workspace/deleteRepository",
  async (repository) => {
    const endpoint = `/api/workspaces/${repository.workspaceId}/repositories/${repository.id}`;
    await client.delete(endpoint);
    return {
      workspaceId: repository.workspaceId,
      id: repository.id,
    };
  },
);

export const editRepository = createAsyncThunk(
  "workspace/editRepository",
  async (repository) => {
    console.log("repository", repository);
    const endpoint = `/api/workspaces/${repository.workspaceId}/repositories/${repository.id}`;
    const response = await client.put(endpoint, {
      ...repository,
    });
    return { workspaceId: repository.workspaceId, ...response.data };
  },
);

export const fetchPullRequests = createAsyncThunk(
  "workspace/fetchPullRequests",
  async (queryObj, state) => {
    //do not fetch if exsists
    const workspace = state
      .getState()
      .workspace.workspaceList.find(
        (workspace) => workspace.id === queryObj.workspaceId,
      );
    const repository = workspace.repositories.find(
      (repository) => repository.id === queryObj.repositoryId,
    );
    if (repository.pullRequests) {
      return {
        workspaceId: queryObj.workspaceId,
        repositoryId: queryObj.repositoryId,
        pullRequests: repository.pullRequests,
      };
    }

    console.log("queryObj", queryObj);
    const { repositoryUrl, workspaceId, repositoryId } = queryObj;
    const response = await client.get("/pull-requests", {
      params: { repositoryUrl },
    });
    return {
      workspaceId,
      repositoryId,
      pullRequests: response.data,
    };
  },
);

export const addReview = createAsyncThunk(
    "workspace/addReview",
    async ( {workspaceId,
                review,
                repositoryId,
                pullRequestUrl}) => {
        const endpoint = `/api/workspaces/${workspaceId}/repositories/${repositoryId}/review`;
        let requestBody = pullRequestUrl + '&' + review;
        const response = await client.post(endpoint, requestBody);
        console.log("response", response);
        return response.data;
    },
);

const workspaceSlice = createSlice({
  name: "workspace",
  initialState: { ...workspaceAdapter.getInitialState(), reviews: {} },
  reducers: {
    sortRepositories: (state, action) => {
      const { workspaceId, sortKey } = action.payload;
      const workspace = state.workspaceList.find(
        (workspace) => workspace.id === workspaceId,
      );
      if (sortKey === "lastCommit") {
        workspace.repositories.sort((a, b) => {
          if (a.lastCommit && b.lastCommit) {
            if (a.lastCommit.date > b.lastCommit.date) return 1;
            if (a.lastCommit.date < b.lastCommit.date) return -1;
            return 0;
          }
          return 0;
        });
        return;
      }

      workspace.repositories.sort((a, b) => {
        if (a[sortKey] > b[sortKey]) return 1;
        if (a[sortKey] < b[sortKey]) return -1;
        return 0;
      });
    },
  },
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
    builder.addCase(addRepository.fulfilled, (state, action) => {
      const index = state.workspaceList.findIndex(
        (workspace) => workspace.id === action.payload.workspaceId,
      );
      if (!state.workspaceList[index].repositories)
        state.workspaceList[index].repositories = [];

      state.workspaceList[index].repositories.push(action.payload);
    });
    builder.addCase(deleteRepository.fulfilled, (state, action) => {
      const index = state.workspaceList.findIndex(
        (workspace) => workspace.id === action.payload.workspaceId,
      );
      state.workspaceList[index].repositories = state.workspaceList[
        index
      ].repositories.filter(
        (repository) => repository.id !== action.payload.id,
      );
    });
    builder.addCase(editRepository.fulfilled, (state, action) => {
      const index = state.workspaceList.findIndex(
        (workspace) => workspace.id === action.payload.workspaceId,
      );
      const repositoryIndex = state.workspaceList[index].repositories.findIndex(
        (repository) => repository.id === action.payload.id,
      );
      state.workspaceList[index].repositories[repositoryIndex] = action.payload;
    });

    builder.addCase(fetchPullRequests.fulfilled, (state, action) => {
      const index = state.workspaceList.findIndex(
        (workspace) => workspace.id === action.payload.workspaceId,
      );
      const repositoryIndex = state.workspaceList[index].repositories.findIndex(
        (repository) => repository.id === action.payload.repositoryId,
      );
      if (action.payload.pullRequests.length === 0) {
        return;
      }
      state.workspaceList[index].repositories[repositoryIndex].pullRequests =
        action.payload.pullRequests || [];
    });

    builder.addCase(addReview.fulfilled, (state, action) => {
        const { prUrl, text } = action.payload;
        console.log(prUrl, text);
        state.reviews[prUrl] = text;
    });
  },
});

export default workspaceSlice.reducer;

export const { sortRepositories } = workspaceSlice.actions;

export const { selectAll: selectAllWorkspace } = workspaceAdapter.getSelectors(
  (state) => state.workspace,
);
