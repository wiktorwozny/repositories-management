import { connect } from "react-redux";

import { useSelector, useDispatch } from "react-redux";
import { fetchWorkspaceList } from "../store/slices/workspaceSlice";
import { useEffect } from "react";
import styled from "styled-components";
import { Button } from "@mui/material";
import AddWorkspace from "../components/workspace/AddWorkspace";
import Workspace from "../components/workspace/Workspace";
import LogoutButton from "../components/authentication/LogoutButton";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
`;

const TopBar = styled.div`
  display: flex;
  justify-content: space-between;
  width: 90%;
  padding: 1rem;
`;

const WorkspaceList = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
  width: 100%;
  align-items: center;
  flex-direction: column;
`;

function Dashboard() {
  const dispatch = useDispatch();
  const workspaceList = useSelector((state) => state.workspace.workspaceList);

  useEffect(() => {
    dispatch(fetchWorkspaceList());
  }, []);

  return (
    <Wrapper>
      <TopBar>
        <h1>Dashboard</h1>
        <AddWorkspace />
        <LogoutButton />
      </TopBar>

      <WorkspaceList>
        {workspaceList?.map((workspace) => (
          <Workspace key={workspace.id} workspace={workspace} />
        ))}
      </WorkspaceList>
    </Wrapper>
  );
}

export default connect()(Dashboard);
