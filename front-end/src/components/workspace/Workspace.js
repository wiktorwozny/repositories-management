import { connect } from "react-redux";
import React from "react";
import { useSelector, useDispatch } from "react-redux";
import EditIcon from "@mui/icons-material/Edit";
import { useEffect } from "react";
import styled from "styled-components";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ContentCopyIcon from '@mui/icons-material/ContentCopy';
import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import AddWorkspace from "./AddWorkspace";
import AddRepository from "./AddRepository";
import pullRequests from "./PullRequests";
import PullRequests from "./PullRequests";
import { sortRepositories } from "../../store/slices/workspaceSlice";

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
  word-break: break-all;
`;

const WorkspaceCourse = styled.h2`
  font-size: 1.5rem;
  margin: 0;
  color: #666;
  word-break: break-all;
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

const RepositoryWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  max-width: 100%;
  width: 90%;
`;

const Repository = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 4px;
`;

const RepositoryInfo = styled.div`
  display: flex;
  padding: 1rem;
`;

const RepositoryManagement = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;

const RepositoryName = styled.h2`
  font-size: 1.5rem;
  margin: 0;
  color: #3b3b3b;
  word-break: break-all;
`;

function Workspace(props) {
  const [expanded, setExpanded] = React.useState(false);
  const [sortKey, setSortKey] = React.useState("default");
  const [selectedRepo, setSelectedRepo] = React.useState(false);
  const handleExpand = () => {
    setExpanded((prev) => !prev);
  };

  const dispatch = useDispatch();

  const handleRepoExpand = (repoid) => {
    if (repoid === selectedRepo) {
      setSelectedRepo(null);
    } else {
      setSelectedRepo(repoid);
    }
  };

  const handleChangeSort = (event) => {
    setSortKey(event.target.value);
    dispatch(
      sortRepositories({
        workspaceId: props.workspace.id,
        sortKey: event.target.value,
      }),
    );
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
          <FormControl>
            <InputLabel id={"sort-by"}>Sort by</InputLabel>
            <Select
              labelId={"sort-by"}
              label={"Sort by"}
              value={sortKey}
              onChange={handleChangeSort}
            >
              <MenuItem value="default">Default</MenuItem>
              <MenuItem value="name">Name</MenuItem>
              <MenuItem value="lastCommit">Last Commit</MenuItem>
            </Select>
          </FormControl>

          <AddRepository workspace={workspace} />
          <AddWorkspace editMode={true} workspace={workspace} />

          <IconButton onClick={handleExpand}>
            {expanded ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />}
          </IconButton>
        </ManagementWrapper>
      </InfoWrapper>

      {expanded && (
        <RepositoryList>
          {workspace.repositories?.map((repository) => (
            <RepositoryWrapper key={repository.id}>
              <Repository>
                <RepositoryInfo>
                  <RepositoryName>{repository.name}</RepositoryName>
                  {" - "}
                  <RepositoryName>
                    <a href={repository.lastCommit?.url}>
                      {repository.lastCommit?.date &&
                        new Date(repository.lastCommit.date).toLocaleString()}
                    </a>
                  </RepositoryName>
                </RepositoryInfo>
                <RepositoryManagement>
                  <IconButton
                    onClick={() => {navigator.clipboard.writeText("git clone " + repository.url)}}
                  >
                    <ContentCopyIcon />
                  </IconButton>
                  <AddRepository
                    editMode={true}
                    repository={repository}
                    workspace={props.workspace}
                  />
                  <IconButton
                    onClick={() => handleRepoExpand(repository.id)}
                  >
                    {selectedRepo === repository.id ? (
                      <ArrowDropUpIcon />
                    ) : (
                      <ArrowDropDownIcon />
                    )}
                  </IconButton>
                </RepositoryManagement>
              </Repository>
              {selectedRepo === repository.id && (
                <PullRequests
                  repositoryUrl={repository.url}
                  repositoryId={repository.id}
                  workspaceId={workspace.id}
                />
              )}
            </RepositoryWrapper>
          ))}
        </RepositoryList>
      )}
    </WorkspaceWrapper>
  );
}

export default connect()(Workspace);
