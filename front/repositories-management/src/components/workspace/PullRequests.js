import React, { useState, useEffect } from "react";
import client from "../../api/client"
import styled from "styled-components";
import { connect } from 'react-redux'

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

const PullRequests = ({ repositoryUrl }) => {
    const [pullRequests, setPullRequests] = useState([]);

    useEffect(() => {
        const fetchPullRequests = async () => {
            try {
                const response = await client.get("/pull-requests", {
                    params: { repositoryUrl },
                });
                setPullRequests(response.data);
                console.log("pull requests fetch", response.data);
            } catch (error) {
                console.error("Error fetching pull requests: ", error);
            }
        };

        fetchPullRequests();
    }, [repositoryUrl]);

    return (
        <PullRequestsWrapper>
            {pullRequests.length > 0 ? (
                pullRequests.map((pr) => (
                    <PullRequestItem key={pr.id}>
                        <PullRequestInfo>
                            <PullRequestLink href={pr.url}>
                                {pr.title}
                            </PullRequestLink>
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