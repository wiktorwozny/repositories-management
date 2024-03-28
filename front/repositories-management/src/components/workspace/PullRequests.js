import React, { useState, useEffect } from "react";
import client from "../../api/client";
import styled from "styled-components";
import { connect, useDispatch, useSelector } from "react-redux";
import { fetchPullRequests } from "../../store/slices/workspaceSlice";

const PullRequestsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
  width: 90%;
`;

const PullRequestItem = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 3px;
`;

const PullRequestInfo = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  gap: 1rem;
  padding: 1rem;
`;

const PullRequestAuthor = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;

const PullRequestLink = styled.a`
  font-size: 1.2rem;
  font-weight: bold;
  margin: 0;
  color: #3b3b3b;
  word-break: break-all;
  &:hover {
    text-decoration: underline;
  }
`;

const PullRequests = ({ repositoryUrl, workspaceId, repositoryId }) => {
  const Workspaces = useSelector((state) => state.workspace.workspaceList);

  const thisPrs = Workspaces.find(
    (workspace) => workspace.id === workspaceId,
  ).repositories.find(
    (repository) => repository.id === repositoryId,
  ).pullRequests;

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(
      fetchPullRequests({
        workspaceId,
        repositoryId,
        repositoryUrl,
      }),
    );
  }, [repositoryUrl]);

  return (
    <PullRequestsWrapper>
      {thisPrs && thisPrs.length > 0 ? (
        thisPrs.map((pr) => (
          <PullRequestItem key={pr.id}>
            <PullRequestInfo>
              <PullRequestLink href={pr.url}>{pr.title}</PullRequestLink>
              <PullRequestAuthor>
                <span>by {pr.userLogin}</span>
              </PullRequestAuthor>
            </PullRequestInfo>
          </PullRequestItem>
        ))
      ) : (
        <p>No pull requests found for this repository.</p>
      )}
    </PullRequestsWrapper>
  );
};

export default connect()(PullRequests);
