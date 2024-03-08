import { connect } from "react-redux";
import React from "react";
import { useSelector, useDispatch } from "react-redux";
import EditIcon from "@mui/icons-material/Edit";
import { useEffect } from "react";
import styled from "styled-components";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import { Button } from "@mui/material";
import IconButton from "@mui/material/IconButton";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import AddWorkspace from "./AddWorkspace";
const WorkspaceWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
  width: 90%;
`;

const InfoWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  //padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
`;

const TitleWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
`;

const WorkspaceName = styled.h1`
  font-size: 2rem;
  margin: 0;
`;

const WorkspaceCourse = styled.h2`
  font-size: 1.5rem;
  margin: 0;
  color: #666;
`;

const ManagementWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;

const RepositoryList = styled.div`
  display: flex;

  justify-content: flex-end;
  align-items: flex-end;
  flex-direction: column;
  width: 100%;
`;

const Repository = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 96%;
  //padding: 1rem;
  border: 1px solid #ddd;
`;
const RepositoryInfo = styled.div`
  display: flex;
  padding: 1rem;
`;

const RepositoryName = styled.h2`
  font-size: 1.5rem;
  margin: 0;
  color: #3b3b3b;
`;

function Workspace(props) {
  const [expanded, setExpanded] = React.useState(false);
  const handleExpand = () => {
    setExpanded((prev) => !prev);
  };

  const workspace = props.workspace;

  return (
    <WorkspaceWrapper>
      <InfoWrapper>
        <TitleWrapper>
          <WorkspaceName>{workspace.name}</WorkspaceName>
          <WorkspaceCourse>{workspace.courseName}</WorkspaceCourse>
        </TitleWrapper>
        <ManagementWrapper>
          <AddWorkspace editMode={true} workspace={workspace} />

          <IconButton onClick={handleExpand}>
            {expanded ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />}
          </IconButton>
        </ManagementWrapper>
      </InfoWrapper>

      {expanded && (
        <RepositoryList>
          {workspace.repositories?.map((repository) => (
            <Repository key={repository.id}>
              <RepositoryInfo>
                <RepositoryName>{repository.name}</RepositoryName>
              </RepositoryInfo>
              <IconButton>
                <EditIcon />
              </IconButton>
            </Repository>
          ))}
        </RepositoryList>
      )}
    </WorkspaceWrapper>
  );
}

export default connect()(Workspace);
